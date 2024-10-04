insert into category (id, name) values (1, '카테고리');

insert into channel (id, name, description, category_id, is_private, max_boards) values (1, '채널', '채널 설명', 1, false, 10);
insert into channel (id, name, description, category_id, is_private, max_boards) values (2, '채널2', '채널 설명2', 1, false, 10);
insert into channel (id, name, description, category_id, is_private, max_boards) values (3, '채널3', '채널 설명3', 1, false, 10);

insert into board (id, name, description, channel_id, is_moderated, is_anonymous) values (1, '게시판', '게시판 설명', 1, false, false);
insert into board (id, name, description, channel_id, is_moderated, is_anonymous) values (2, '게시판2', '게시판 설명2', 1, false, false);
insert into board (id, name, description, channel_id, is_moderated, is_anonymous) values (3, '게시판3', '게시판 설명3', 1, false, false);
insert into board (id, name, description, channel_id, is_moderated, is_anonymous) values (4, '게시판4', '게시판 설명4', 2, false, false);
insert into board (id, name, description, channel_id, is_moderated, is_anonymous) values (5, '게시판5', '게시판 설명5', 3, false, false);

insert into members (id, username, email, password, role) values (1, 'test1', 'test@test.com', 'test', 'USER');
insert into members (id, username, email, password, role) values (2, 'test2', 'test@test2.com', 'test', 'USER');
insert into members (id, username, email, password, role) values (3, 'test3', 'test@test3.com', 'test', 'USER');
insert into members (id, username, email, password, role) values (4, 'test4', 'test@test4.com', 'test', 'USER');
insert into members (id, username, email, password, role) values (5, 'test5', 'test@test5.com', 'test', 'USER');

insert into post (id, title, content, view_count, member_id, board_id) values (1, '제목1', '내용1', 0, 1, 1);
insert into post (id, title, content, view_count, member_id, board_id) values (2, '제목2', '내용2', 0, 2, 1);
insert into post (id, title, content, view_count, member_id, board_id) values (3, '제목3', '내용3', 0, 3, 2);
insert into post (id, title, content, view_count, member_id, board_id) values (4, '제목4', '내용4', 0, 4, 2);
insert into post (id, title, content, view_count, member_id, board_id) values (5, '제목5', '내용5', 0, 5, 3);
insert into post (id, title, content, view_count, member_id, board_id) values (6, '제목6', '내용6', 0, 1, 3);
insert into post (id, title, content, view_count, member_id, board_id) values (7, '제목7', '내용7', 0, 2, 4);
insert into post (id, title, content, view_count, member_id, board_id) values (8, '제목8', '내용8', 0, 3, 4);
insert into post (id, title, content, view_count, member_id, board_id) values (9, '제목9', '내용9', 0, 4, 5);
insert into post (id, title, content, view_count, member_id, board_id) values (10, '제목10', '내용10', 0, 5, 5);

insert into comment (id, content, member_id, post_id) values (1, '댓글1', 1, 1);
insert into comment (id, content, member_id, post_id) values (2, '댓글2', 2, 1);
insert into comment (id, content, member_id, post_id) values (3, '댓글3', 3, 1);
insert into comment (id, content, member_id, post_id) values (4, '댓글4', 4, 1);
insert into comment (id, content, member_id, post_id, parent_id) values (5, '댓글5', 5, 1, 1);
insert into comment (id, content, member_id, post_id, parent_id) values (6, '댓글6', 1, 1, 1);
insert into comment (id, content, member_id, post_id, parent_id) values (7, '댓글7', 2, 1, 1);
