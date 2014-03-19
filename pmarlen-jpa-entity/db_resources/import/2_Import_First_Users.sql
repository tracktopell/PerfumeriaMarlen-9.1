DELETE FROM USUARIO_PERFIL;
DELETE FROM USUARIO;

INSERT INTO USUARIO VALUES ('root'		,1,'root'									,'977244cbc826a0f25d07a07f194835b1'	,'desarrollador@perfumeriamarlen.com.mx',1);
INSERT INTO USUARIO VALUES ('uleon'		,1,'ULISES LEON RESENDIZ'					,'9c1d2298a73d21aac61a5f201e255b34'	,'uleon@perfumeriamarlen.com.mx',1);
INSERT INTO USUARIO VALUES ('dkgarcia'	,1,'DIANA KAREN GARCIA FRAUSTO'				,'2fc223d04ab6143c1ca46cacd69d6e22'	,'dkgarcia@perfumeriamarlen.com.mx',1);

INSERT INTO USUARIO_PERFIL VALUES ('root'		,'root');
INSERT INTO USUARIO_PERFIL VALUES ('root'		,'pmarlenuser');
INSERT INTO USUARIO_PERFIL VALUES ('root'		,'admin');
INSERT INTO USUARIO_PERFIL VALUES ('root'		,'finances');

INSERT INTO USUARIO_PERFIL VALUES ('uleon'		,'pmarlenuser');
INSERT INTO USUARIO_PERFIL VALUES ('uleon'		,'admin');
INSERT INTO USUARIO_PERFIL VALUES ('uleon'		,'finances');

INSERT INTO USUARIO_PERFIL VALUES ('dkgarcia'	,'finances');
INSERT INTO USUARIO_PERFIL VALUES ('dkgarcia'	,'pmarlenuser');

