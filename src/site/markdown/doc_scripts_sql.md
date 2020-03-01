## Scripts SQL

Les scripts SQL permettent d'insérer des données dans la base, et ainsi de tester le bon fonctionnement de l'application.  
La création des scripts SQL est automatisée avec un programme écrit en Mathematica.  
L'exemple ci-dessous montre un bout de programme Mathematica qui écrit dans le fichier SQL les requêtes permettant d'insérer les données liées à la table "pret\_emprunt\_manuel" :  

	cheminFichierScript = "C:\\Users\\gregg\\Documents\\openclassrooms\\developpeur_applications_java\\06_creer_site_communautaire_autour_escalade\\scripts\\insert_data _db.sql";  

	ecrire[texte_] := WriteLine[outputStream, texte];  
	requeteInsert[table_String, colonnes_List, valeurs_List] := "INSERT INTO public."<> table <>" ("<>StringRiffle[colonnes, ", "]<> ") VALUES (" <>StringRiffle[valeurs, ", "]<> ");";  
	requeteInitialiserSequence[sequenceString_String, sequenceInteger_Integer] :="SELECT setval('public." <> sequenceString <> "', " <> ToString[sequenceInteger] <> ", true);";  

	PRET_EMPRUNT_MANUEL = "pret_emprunt_manuel";  
	PRET_EMPRUNT_MANUEL_COLUMNS_HEADERS = {"id", "date", "emprunteur", "manuel_id", "preteur"};  
	PRET_EMPRUNT_MANUEL_ID_SEQ = "pret_emprunt_manuel_id_seq";  
	PRET_EMPRUNT_MANUEL_SEQ = 0;  
	MANUEL_ETAT_INDISPONIBLE = "INDISPONIBLE";  
	MANUEL_ETAT_DISPONIBLE = "DISPONIBLE";  
	NULL = "NULL";  
	ID = "id";  

	pretEmpruntManuel = {};  

	formatSql[str_] := If[str =!= NULL, StringInsert[StringReplace[str, "'" -> "''"], "'", {1, -1}]], str];  
	formatDate[annee_, mois_, jour_, heure_, minute_, seconde_] := DateString[DateObject[DateObject[{annee, mois, jour}], TimeObject[{heure, minute, seconde}]], {"'", "Year", "-", "Month", "-", "Day", " ", "Hour", ":", "Minute", ":", "Second", "'"}];  

	creerPretEmpruntManuel[date_, emprunteur_, manuelId_, preteur_] := With[{datas = <|ID -> ToString[++PRET_EMPRUNT_MANUEL_SEQ], "date" -> formatDate@@date, "emprunteur" -> formatSql[emprunteur], "manuelId" -> manuelId, "preteur" -> preteur|>}, AppendTo[pretEmpruntManuel, datas]; datas];  
	ecrirePretEmpruntManuel[pretEmpruntManuel_] := ecrire[requeteInsert[PRET_EMPRUNT_MANUEL, PRET_EMPRUNT_MANUEL_COLUMNS_HEADERS, Values[pretEmpruntManuel]]];  

	SeedRandom[];  
	manuel = RandomSample[Function[{data}, Pick[data, # === "id" || # === "authentification_email" || # === "etat" &/@ Keys[data]]][#] &/@ manuelDatas, Floor[Length[manuelDatas] * .9]];  
	manuelIndisponible = DeleteCases[#, ""] &/@ First[Multicolumn[Select[manuel, #["etat"] === "'" <> MANUEL_ETAT_INDISPONIBLE <> "'" &], {2, Automatic}]];  
	dateRange = DateRange[{2019, 7, 1}, {2020, 2, 29}];  

	Function[{manuelEmail},  
		(  
			SeedRandom[];  
			ecrirePretEmpruntManuel[creerPretEmpruntManuel[  
				(*date*)  
				{#, #2, #3, RandomChoice[Range[23]], RandomChoice[Range[59]], RandomChoice[Range[59]]}& @@ #,  
				(*emprunteur*)  
				RandomChoice[emailsActifs],  
				(*manuel_id*)  
				manuelEmail["id"],  
				(*preteur*)  
				manuelEmail["authentification_email"]  
			]]  
		)  
	&[RandomChoice[dateRange]]] /@ First[manuelIndisponible];  

	ecrire[""];  
	ecrire[requeteInitialiserSequence[PRET_EMPRUNT_MANUEL_ID_SEQ, PRET_EMPRUNT_MANUEL_SEQ]];  

	ecrire[""];  
	ecrire["COMMIT;"];  

	Close[outputStream];