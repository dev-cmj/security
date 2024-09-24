insert into category (id, name) values (1, '카테고리');

insert into channel (id, name, description, category_id, is_private, max_boards) values (1, '채널', '채널 설명', 1, false, 10);
insert into board (id, name, description, channel_id, is_moderated, is_anonymous) values (1, '게시판', '게시판 설명', 1, false, false);