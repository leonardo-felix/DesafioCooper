insert into tipo_telefone(descricao, celular) values
  ('Celular', 1),
  ('Residencial', 0),
  ('Comercial', 0);

insert into usuario_papel(nome, admin) values ('Administrador', 1), ('Comuns', 0);

-- senha 123456
insert into usuario(login, senha, usuario_papel_id) values ('admin', '$2y$12$kd2LDrNIn.Wo8JINSpJpSe0K4UvX.hJnfJAsdbj7LzlbNVtGDbiLm', 1)