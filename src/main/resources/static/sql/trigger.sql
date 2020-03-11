-- 叫料表触发器
create trigger call_off_trigger
  after insert on call_off
  for each row execute procedure call_odd_insert_procedure();


-- 叫料存储过程
create or replace function call_odd_insert_procedure()
  returns trigger as $calloff$
  begin
    insert into notice(notice_message, storeroom_id) values (concat('您有新的备料任务,工单号为 ',new.work_number),new.storeroom_id);
    return new;
end;
$calloff$
LANGUAGE plpgsql;


-- 退料存储过程
create or replace function withdrawal_insert_procedure()
  returns trigger as $withdrawal$
begin
  insert into notice(notice_message, storeroom_id) values (concat('您有新的退料任务,工单号为 ',new.work_number),new.storeroom_id);
  return new;
end;
$withdrawal$
LANGUAGE plpgsql;

-- 退料触发器
create trigger withdrawal_trigger
  after insert on withdrawal
  for each row execute procedure withdrawal_insert_procedure();

