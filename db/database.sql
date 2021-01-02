create table users
(
	id int not null
		primary key,
	first_name varchar(255) not null,
	last_name varchar(255) not null,
	email varchar(128) not null,
	password varchar(255) not null,
	created_at timestamp default CURRENT_TIMESTAMP not null,
	updated_at timestamp default CURRENT_TIMESTAMP null,
	role enum('ADMIN', 'CUSTOMER') default 'CUSTOMER' not null,
	-- deleted_at timestamp null,
	banned_at timestamp null,
	constraint users_email_uindex
		unique (email)
);

create table products
(
	id int not null
		primary key,
	name varchar(255) not null,
	description text null,
	image_url varchar(1024) null,
	user_id int not null,
	created_at timestamp default CURRENT_TIMESTAMP not null,
	updated_at timestamp default CURRENT_TIMESTAMP null,
	date date not null,
	constraint products_date_uindex
		unique (date),
	constraint products_users_id_fk
		foreign key (user_id) references users (id)
);

create table questions
(
	id int not null
		primary key,
	title varchar(255) not null,
	subtitle text null,
	product_id int not null,
	created_at timestamp default CURRENT_TIMESTAMP not null,
	updated_at timestamp default CURRENT_TIMESTAMP null,
	constraint questions_products_id_fk
		foreign key (product_id) references products (id)
);

create table submissions
(
	id int not null
		primary key,
	user_id int not null,
	product_id int not null,
	created_at timestamp default CURRENT_TIMESTAMP not null,
	updated_at timestamp default CURRENT_TIMESTAMP null,
	age int null,
	sex enum('M', 'F') null,
	expertise_level enum('LOW', 'MEDIUM', 'HIGH') null,
	-- deleted_at timestamp null,
	status enum('CREATED', 'CANCELED', 'CONFIRMED') default 'CREATED' null,
	constraint submissions_products_id_fk
		foreign key (product_id) references products (id),
	constraint submissions_users_id_fk
		foreign key (user_id) references users (id)
);

create table responses
(
	id int not null
		primary key,
	body text not null,
	question_id int not null,
	submission_id int not null,
	constraint responses_questions_id_fk
		foreign key (question_id) references questions (id),
	constraint responses_submissions_id_fk
		foreign key (submission_id) references submissions (id)
);

create table bad_words
(
    text varchar(50) null
);



