/* Database: PostgreSql. Generation date: 2023-02-02 14:37:54:064 */
/* Entity Tarefa */
create table tarefas (
	id UUID NOT NULL,
	descricao VARCHAR(255) NOT NULL,
	usuger VARCHAR(255),
	datger TIMESTAMP,
	usualt VARCHAR(255),
	datalt TIMESTAMP,
	ext JSONB
);


/* Creating index for customization column */
create index tarefas_ext on tarefas using gin (ext);

/* Entity Participante */
create table participantes (
	id UUID NOT NULL,
	nome VARCHAR(255) NOT NULL,
	usuger VARCHAR(255),
	datger TIMESTAMP,
	usualt VARCHAR(255),
	datalt TIMESTAMP,
	ext JSONB
);


/* Creating index for customization column */
create index participantes_ext on participantes using gin (ext);

/* Join Tables */
/* master: Tarefa as tarefas, detail: Participante as participantes */
create table tarefas_participantes (
	tarefas_id UUID NOT NULL,
	participantes_id UUID NOT NULL
);

/* Primary Key Constraints */
alter table tarefas_participantes add constraint pk_tarefas_participantes primary key(tarefas_id, participantes_id);
alter table tarefas add constraint pk_tarefas_id primary key(id);
alter table participantes add constraint pk_participantes_id primary key(id);

/* Foreign Key Constraints */
alter table tarefas_participantes add constraint fkzoinmnw4gzd21o1rqceqh4l7ymk3 foreign key (tarefas_id) references tarefas (id);
alter table tarefas_participantes add constraint fkex2oso3gi118gfyh01kqzddeylnm foreign key (participantes_id) references participantes (id);

/* Unique Key Constraints */

/* Sequences for auto increment entity ids */
