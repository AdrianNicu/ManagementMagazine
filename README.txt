Tanase Adrian-Nicolae
322CC
Grad de dificultate pe o scara 1-10: 6
Timp alocat: 25-30 ore

Am implementat fiecare clasa in fisieru ei, in afara de clasele comparator.
Aplicatia consta in 4 ferestre consecutive ce au urmatoarele roluri:logare
incarcarea fisierelor, administrarea produselor si statistici.
Fereastra de logare preia informatiile introduse de utilizator si le compara
cu cele din fisierul users.txt.Daca acestea se regasesc in fisier, se trece la
urmatoarea fereastra.In fereastra de incarcare utilizatorul are posibilitatea
sa incarce cele 3 fisiere de oriunde din calculatorul de pe care se ruleaza
aplicatia.La apasarea butonului incarca se genereaza fisierul out.txt si se trece
la fereastra de administrare.Aici utilizatorul are posibilitatea de a edita/ordona
produsele (afisate intr-un tabel) ,de a adauga produse ,de a cauta un anumit produs
intr-un magazin(pe baza facturilor) sau de a sterge produse.In urma adaugarii,
stergerii,editarii unui produs, fisierul produse.txt se actualizeaza imediat.
Pentru clasa Gestiune am folosit Singleton pattern iar pentru crearea magazinelor
am folosit Factory pattern.
	Mentionez ca am implementat cele 3 taskuri bonus.
	Nu am folosit window builder.
	Am adaugat JPaneluri customizate in care am desenat imagini pentru un aspect
placut.