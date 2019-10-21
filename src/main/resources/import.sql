BEGIN TRANSACTION;


-- ================================================================================
-- authentification
-- ================================================================================
INSERT INTO public.authentification (email, actif_q, mot_de_passe) VALUES ('test.admin@gaugiciel.app', TRUE, crypt('testadmin', gen_salt('bf',8)));
INSERT INTO public.authentification (email, actif_q, mot_de_passe) VALUES ('elie.menetries@lesamisdelescalade.fr', TRUE, crypt('elie', gen_salt('bf',8)));
INSERT INTO public.authentification (email, actif_q, mot_de_passe) VALUES ('test.ami@gaugiciel.app', TRUE, crypt('testami', gen_salt('bf',8)));
INSERT INTO public.authentification (email, actif_q, mot_de_passe) VALUES ('amaury.clerisseau@semifun.fr', TRUE, crypt('amaury', gen_salt('bf',8)));
INSERT INTO public.authentification (email, actif_q, mot_de_passe) VALUES ('greg.gautier@hotmail.com', TRUE, crypt('greg', gen_salt('bf',8)));


-- ================================================================================
-- role
-- ================================================================================
INSERT INTO public.role (role) VALUES ('AMI');
INSERT INTO public.role (role) VALUES ('ADMINISTRATEUR');


-- ================================================================================
-- profil
-- ================================================================================
INSERT INTO public.profil (authentification_email, role_role) VALUES ('test.admin@gaugiciel.app', 'AMI');
INSERT INTO public.profil (authentification_email, role_role) VALUES ('elie.menetries@lesamisdelescalade.fr', 'ADMINISTRATEUR');
INSERT INTO public.profil (authentification_email, role_role) VALUES ('greg.gautier@hotmail.com', 'AMI');
INSERT INTO public.profil (authentification_email, role_role) VALUES ('test.admin@gaugiciel.app', 'ADMINISTRATEUR');
INSERT INTO public.profil (authentification_email, role_role) VALUES ('amaury.clerisseau@semifun.fr', 'AMI');
INSERT INTO public.profil (authentification_email, role_role) VALUES ('test.ami@gaugiciel.app', 'AMI');
INSERT INTO public.profil (authentification_email, role_role) VALUES ('greg.gautier@hotmail.com', 'ADMINISTRATEUR');
INSERT INTO public.profil (authentification_email, role_role) VALUES ('elie.menetries@lesamisdelescalade.fr', 'AMI');


-- ================================================================================
-- utilisateur
-- ================================================================================
INSERT INTO public.utilisateur (nom, prenom, authentification_email) VALUES ('Gautier', 'Gregory', 'greg.gautier@hotmail.com');
INSERT INTO public.utilisateur (nom, prenom, authentification_email) VALUES ('Ami', 'Test', 'test.ami@gaugiciel.app');
INSERT INTO public.utilisateur (nom, prenom, authentification_email) VALUES ('Clerisseau', 'Amaury', 'amaury.clerisseau@semifun.fr');
INSERT INTO public.utilisateur (nom, prenom, authentification_email) VALUES ('Menetries', 'Elie', 'elie.menetries@lesamisdelescalade.fr');
INSERT INTO public.utilisateur (nom, prenom, authentification_email) VALUES ('Admin', 'Test', 'test.admin@gaugiciel.app');


COMMIT;
