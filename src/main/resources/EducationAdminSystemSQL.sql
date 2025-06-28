
-- 学生信息管理表
CREATE TABLE Student (
    s_id INT primary key,
    s_name VARCHAR(10),
    s_sex VARCHAR(4),
    s_password VARCHAR(100),
    s_class_id INT,
    foreign key (s_class_id) references Class(c_id) on delete set null
);

-- 教师信息管理表
CREATE TABLE Teacher (
    t_id INT primary key,
    t_name VARCHAR(10),
    t_password VARCHAR(100)
);


-- 课程信息管理表
CREATE TABLE Course (
    c_id INT primary key,
    c_name VARCHAR(20),
    t_id INT,
    max_students INT DEFAULT 50;
    current_enrollment INT DEFAULT 0;
    credits INT DEFAULT 3; 
    foreign key (t_id) references Teacher(t_id) on delete set null
);


-- 班级信息管理表
CREATE TABLE Class (
    c_id INT primary key,
    s_number INT default 0
);

-- 教室信息表
CREATE TABLE Classroom (
    c_id INT primary key,
    capacity INT default 60
);


-- 学籍管理表
CREATE TABLE Roll (
    s_id INT primary key, 
    s_status VARCHAR(50),
    s_entrance_time DATE,
    s_graduate_time DATE,
    s_leave_time DATE
);

--学籍历史记录
create table rollhistory(
    s_id INT, 
    s_status VARCHAR(50),
    s_entrance_time DATE,
    s_graduate_time DATE,
    s_leave_time DATE,
)

-- 预约教室信息表
CREATE TABLE OrderClassroom (
    c_id INT,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    t_id INT,
    foreign key (c_id) references Classroom(c_id) on delete cascade ,
    foreign key (t_id) references Teacher(t_id) on delete cascade,
    CONSTRAINT uk_orderclassroom UNIQUE (c_id, start_time, end_time),
    CONSTRAINT check_time_order CHECK (start_time < end_time)
);


-- 出勤信息表
CREATE TABLE Attendance (
    t_id INT,
    t_arrive_time TIMESTAMP default CURRENT_TIMESTAMP,
    t_leave_time TIMESTAMP,
    a_date DATE default CURRENT_DATE,
    foreign key (t_id) references Teacher(t_id) on delete cascade
);

-- 考试信息表
CREATE TABLE exam (
    c_id INT,
    classroom_id INT,
    e_time INT,
    e_date DATE,
    class_id INT,
    foreign key (c_id) references Course(c_id) on delete cascade,
    foreign key (classroom_id) references Classroom(c_id) on delete set not null,
    foreign key (class_id) references Class(c_id)on delete cascade
);

drop table schedule;
-- 课程安排表
CREATE TABLE Schedule (
    c_id INT,
    class_id INT,
    s_weekday int ,
    start_time TIME,
    end_time TIME,
    classroom_id INT,
    foreign key (c_id) references Course(c_id) on delete cascade,
    foreign key (classroom_id) references Classroom(c_id) on delete set null,
    foreign key (class_id) references Class(c_id) on delete set null,
    primary key (s_weekday,start_time,end_time,classroom_id)
);

-- 选课表
CREATE TABLE ChooseCourse (
    s_id INT,
    normal_score INT,
    experiment_score INT,
    stage_score INT,
    test_score INT,
    c_id INT,
    --总评成绩
    average_score NUMERIC(5,2) ,
    foreign key (s_id) references Student(s_id) on delete cascade,
    foreign key (c_id) references Course(c_id) on delete cascade
);



--预约教室函数

CREATE OR REPLACE PROCEDURE sp_book_classroom(
    p_c_id IN INTEGER,
    p_start_time IN TIMESTAMP,
    p_end_time IN TIMESTAMP,
    p_t_id IN INTEGER,
    p_message OUT VARCHAR2(200)
)
AS
    v_conflict_count INTEGER;
    v_classroom_exists INTEGER;
BEGIN
    -- 1. 检查教室是否存在
    SELECT COUNT(1) INTO v_classroom_exists
    FROM Classroom
    WHERE c_id = p_c_id;
    
    IF v_classroom_exists = 0 THEN
        p_message := '预约失败：教室ID ' || p_c_id || ' 不存在';
        RETURN;
    END IF;
    
    -- 2. 检查时间冲突并加行级锁
    BEGIN
        SELECT COUNT(1) INTO v_conflict_count
        FROM OrderClassroom
        WHERE c_id = p_c_id
        AND NOT (end_time <= p_start_time OR start_time >= p_end_time)
        FOR UPDATE OF c_id, start_time, end_time;
        
        IF v_conflict_count > 0 THEN
            p_message := '预约失败：该教室在指定时间已被预约';
            RETURN;
        END IF;
    EXCEPTION
        WHEN OTHERS THEN
            p_message := '预约失败：检查时间冲突时发生异常';
            RETURN;
    END;
    
    -- 3. 插入预约记录（无booking_id字段）
    INSERT INTO OrderClassroom (
        c_id, 
        start_time, 
        end_time, 
        t_id
    ) VALUES (
        p_c_id,
        p_start_time,
        p_end_time,
        p_t_id
    );
    
    -- 4. 返回成功信息
    p_message := '教室预约成功';
    COMMIT;
    
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        IF SQLCODE = -1062 THEN  -- 唯一约束冲突（如重复预约）
            p_message := '预约失败：相同教室和时间的预约已存在';
        ELSE
            p_message := '预约失败：发生未知错误 - ' || SQLERRM;
        END IF;
END;
/


--选课函数

CREATE OR REPLACE PROCEDURE sp_student_enroll_course(
    p_student_id IN INTEGER,
    p_course_id IN INTEGER,
    p_message OUT VARCHAR2(200)
)
AS
    v_max_students INTEGER;
    v_current_enrollment INTEGER;
    v_course_credits INTEGER;
    v_total_credits NUMBER;
    v_conflict_count INTEGER;
    v_already_enrolled INTEGER;
    v_lock_count INTEGER;
    v_dummy INTEGER;
begin
    -- 替换原来的单独 select 语句
    BEGIN
        select 1 into v_dummy 
        from ChooseCourse
        where s_id = p_student_id and c_id = p_course_id
        for update of chooseCourse;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            v_dummy := 0; -- 无记录时赋默认值，不影响后续逻辑
    END;
   
    -- 1. 检查学生是否已选该课程（带行级锁）
    SELECT COUNT(1) INTO v_already_enrolled
    FROM ChooseCourse
    WHERE s_id = p_student_id AND c_id = p_course_id;
    -- 锁定学生选课记录
    
    IF v_already_enrolled > 0 THEN
        p_message := '选课失败：学生已选该课程';
        RETURN;
    END IF;
    
    -- 2. 获取课程信息并锁定课程记录（防止并发选课超员）
    SELECT max_students, current_enrollment, credits
    INTO v_max_students, v_current_enrollment, v_course_credits
    FROM Course
    WHERE c_id = p_course_id
    FOR UPDATE ;
--    OF current_enrollment;  -- 锁定课程当前选课人数字段
    
    -- 3. 检查课程是否已选满（带并发安全检查）
    IF v_current_enrollment >= v_max_students THEN
        p_message := '选课失败：课程已选满';
        RETURN;
    END IF;
    
    -- 4. 检查学生当前学分总和
    --coalesce用于返回列表第一个非空值
    SELECT COALESCE(SUM(c.credits), 0) INTO v_total_credits
    FROM ChooseCourse cc
    JOIN Course c ON cc.c_id = c.c_id
    WHERE cc.s_id = p_student_id;
    
    IF v_total_credits + v_course_credits > 25 THEN
        p_message := '选课失败：选课总学分超过25分限制';
        RETURN;
    END IF;
    
    -- 5. 检查时间冲突（假设Schedule表中有weekday, start_time, end_time字段）
    SELECT COUNT(1) INTO v_conflict_count
    FROM Schedule s1
    JOIN Schedule s2 ON s1.c_id = p_course_id AND s2.c_id IN (
        SELECT c_id FROM ChooseCourse WHERE s_id = p_student_id
    )
    WHERE s1.s_weekday = s2.s_weekday
    AND NOT (s1.end_time < s2.start_time OR s1.start_time > s2.end_time);
    
    IF v_conflict_count > 0 THEN
        p_message := '选课失败：课程时间冲突';
        RETURN;
    END IF;
    
    -- 6. 所有检查通过，执行选课（事务保护）
    INSERT INTO ChooseCourse (s_id, c_id) VALUES (p_student_id, p_course_id);
    
    -- 7. 更新课程选课人数（原子操作）
    UPDATE Course
    SET current_enrollment = current_enrollment + 1
    WHERE c_id = p_course_id
    AND current_enrollment = v_current_enrollment;  -- 乐观锁检查
    
    IF SQL%ROWCOUNT = 0 THEN
        -- 更新失败说明并发冲突（其他事务已抢先更新）
        p_message := '选课失败：课程选课人数已被其他用户更新，请重试';
        ROLLBACK;
        RETURN;
    END IF;
    
    COMMIT;
    p_message := '选课成功';
    
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        p_message := '选课失败：发生未知错误 - ' || SQLERRM;
END;
/



--添加课程安排过程

CREATE OR REPLACE PROCEDURE AddScheduleWithCheck(
    p_c_id IN INTEGER,               -- 课程 ID
    p_class_id IN INTEGER,           -- 班级 ID
    p_s_weekday IN INTEGER,          -- 周几（如 1=周一、2=周二...）
    p_start_time IN TIME,        -- 开始时间
    p_end_time IN TIME,          -- 结束时间
    p_classroom_id IN INTEGER,       -- 教室 ID
    p_result out VARCHAR(255)    -- 执行结果反馈
)
AS 
    conflict_count INT := 0;    -- 冲突计数
begin
    -- 1. 校验【班级 + 时间】冲突：同班级、同周几，时间是否重叠
    SELECT COUNT(*) INTO conflict_count
    FROM Schedule
    WHERE class_id = p_class_id
      AND s_weekday = p_s_weekday
      AND (
          -- 时间重叠判定：开始时间 < 已有结束时间 且 已有开始时间 < 结束时间
          start_time < p_end_time 
          AND end_time > p_start_time
      );

    -- 若班级冲突，直接返回结果
    IF conflict_count > 0 THEN
        p_result := '冲突：该班级同时间已有课程安排！';
        RETURN;
    END IF;

    -- 2. 校验【教室 + 时间】冲突：同教室、同周几，时间是否重叠
    SELECT COUNT(*) INTO conflict_count
    FROM Schedule
    WHERE classroom_id = p_classroom_id
      AND s_weekday = p_s_weekday
      AND (
          start_time < p_end_time 
          AND end_time > p_start_time
      );

    -- 若教室冲突，直接返回结果
    IF conflict_count > 0 THEN
        p_result := '冲突：该教室同时间已有课程安排！';
        RETURN;
    END IF;
    
    -- 若时间冲突，直接返回结果
    IF p_start_time>=p_end_time THEN
        p_result := '起始时间需要在结束时间之前！';
        RETURN;
    END IF;

    -- 3. 无冲突则执行插入
    INSERT INTO Schedule (c_id, class_id, s_weekday, start_time, end_time, classroom_id)
    VALUES (p_c_id, p_class_id, p_s_weekday, p_start_time, p_end_time, p_classroom_id);

    -- 返回成功结果
    p_result := '课程安排添加成功（无冲突）';
END;
/


--删除课程安排

CREATE OR REPLACE PROCEDURE DeleteSchedule(
    p_c_id  in INTEGER,               -- 课程 ID
    p_class_id IN INTEGER,           -- 班级 ID
    p_s_weekday IN INTEGER,          -- 周几
    p_result OUT VARCHAR(255)    -- 执行结果反馈
)
AS 
    record_count INT := 0;      -- 记录计数
BEGIN
    -- 1. 检查待删除记录是否存在
    SELECT COUNT(*) INTO record_count
    FROM Schedule
    WHERE c_id = p_c_id
      AND class_id = p_class_id
      AND s_weekday = p_s_weekday;

    -- 2. 存在则删除，否则提示无记录
    IF record_count > 0 THEN
        DELETE FROM Schedule
        WHERE c_id = p_c_id
          AND class_id = p_class_id
          AND s_weekday = p_s_weekday;
        
        p_result := '课程安排删除成功';
    ELSE
        p_result := '未找到对应课程安排（c_id: ' || p_c_id || '，class_id: ' || p_class_id || '，周' || p_s_weekday || '）';
    END IF;
END;
/



--改动学籍表

CREATE OR REPLACE PROCEDURE manage_student_roll(
    p_s_id          IN  INTEGER,          
    p_new_status    IN  VARCHAR(50),     
    p_new_entrance  IN  DATE DEFAULT NULL,
    p_new_graduate  IN  DATE DEFAULT NULL,
    p_new_leave     IN  DATE DEFAULT NULL,
    p_result_msg    OUT VARCHAR(200)     
)
AS 
    v_old_status       VARCHAR(50);       
    v_exists           INTEGER;    
    v_dummy            INTEGER;
    v_student_exists   INTEGER;            
BEGIN
    -- 1. 并发控制（行级锁 + 检查存在性）
    -- 先加行锁
    SELECT 1 INTO v_dummy
    FROM Roll
    WHERE s_id = p_s_id
    FOR UPDATE; 

    -- 再统计存在性
    SELECT COUNT(1) INTO v_exists
    FROM Roll
    WHERE s_id = p_s_id; 

    IF v_exists = 0 THEN
        p_result_msg := '错误：学籍信息（s_id=' || p_s_id || '）不存在';
        RETURN;
    END IF;

    -- 2. 备份历史记录
    SELECT s_status INTO v_old_status
    FROM Roll
    WHERE s_id = p_s_id;

    INSERT INTO rollhistory (
        s_id, s_status, s_entrance_time, s_graduate_time, s_leave_time
    )
    SELECT 
        s_id, s_status, s_entrance_time, s_graduate_time, s_leave_time
    FROM Roll
    WHERE s_id = p_s_id;

    -- 3. 更新学籍主表
    UPDATE Roll
    SET 
        s_status = COALESCE(p_new_status, s_status),
        s_entrance_time = COALESCE(p_new_entrance, s_entrance_time),
        s_graduate_time = COALESCE(p_new_graduate, s_graduate_time),
        s_leave_time = COALESCE(p_new_leave, s_leave_time)
    WHERE s_id = p_s_id;

    -- 4. 退学联动删除Student表数据
    IF p_new_status = '退学' THEN
        SELECT COUNT(1) INTO v_student_exists
        FROM Student
        WHERE s_id = p_s_id;

        IF v_student_exists > 0 THEN
            DELETE FROM Student WHERE s_id = p_s_id;
            p_result_msg := '成功：学籍状态已更新为"退学"，并删除Student表记录';
        ELSE
            p_result_msg := '警告：学籍设为退学，但Student表无对应记录（s_id=' || p_s_id || '）';
        END IF;
    ELSE
        p_result_msg := '成功：学籍状态已更新为"' || p_new_status || '"';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        p_result_msg := '操作失败：' || SQLERRM;
END;
/


