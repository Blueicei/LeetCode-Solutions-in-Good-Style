# 1
select
    DATE_FORMAT (a.t_time, '%Y-%m') time,
    sum(a.t_amount) total
from
    trade a
    join customer b on a.t_cus = b.c_id
where
    b.c_name = 'Tom'
  and a.t_type = 1
  and year (a.t_time) = 2023
group by
    DATE_FORMAT (a.t_time, '%Y-%m')
order by
    date_format (a.t_time, "%Y-%m");
# 2
with
    t1 as (
        select
            user_id,
            a.room_id,
            a.room_type,
            datediff (checkout_time, checkin_time) as days
        from
            guestroom_tb as a
                right join checkin_tb as b on a.room_id = b.room_id
        where
            date (checkin_time) = '2022-06-12'
    )
select
    user_id,
    room_id,
    room_type,
    days
from
    t1
where
    days > 1
order by
    days asc,
    room_id asc,
    user_id desc;
# 3
SELECT
    SUM(
        CASE
            WHEN course IS NULL THEN 0
            ELSE LENGTH (course) - LENGTH (REPLACE (course, ',', '')) + 1
        END
    ) AS staff_nums
FROM
    cultivate_tb
# 4
select
    c.staff_id,
    s.staff_name
from
    staff_tb s
        left join cultivate_tb c on s.staff_id = c.staff_id
where
    c.course like '%course3%'
order by
    c.staff_id
# 5
with
    t1 as (
    select distinct
    rt.rec_info_l,
    rt.rec_user,
    uat.score
    from
    recommend_tb rt
    join user_action_tb uat on rt.rec_info_l = uat.hobby_l
    and rt.rec_user = uat.user_id
    )
select
    round(avg(score), 3) as avg_score
from
    t1
# 6
select
    product_name,
    total_sales,
    category_rank
from
    (
        select
            t1.product_id as product_id,
            t1.name as product_name,
            sum(t2.quantity) as total_sales,
            t1.category as category,
            row_number() over (
                partition by t1.category
                order by sum(t2.quantity) desc
            ) as category_rank
        from
            products as t1
                join orders as t2 on t1.product_id = t2.product_id
        GROUP BY
            t1.product_id,
            t1.name
    ) as a
order by
    category asc,
    total_sales desc
