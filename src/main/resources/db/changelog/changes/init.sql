
CREATE TABLE if not exists public.poll (
	id uuid primary key,
	created_at timestamp NOT NULL,
	updated_at timestamp NOT NULL,
	is_active bool NULL,
	end_date timestamp NULL,
	poll_name varchar(255) NULL,
	start_date timestamp NULL
);

CREATE TABLE if not exists public.question (
	id uuid primary key,
	created_at timestamp NOT NULL,
	updated_at timestamp NOT NULL,
	content varchar(255) NULL,
	sort_by int NULL,
    poll_id uuid REFERENCES poll (id)
);


