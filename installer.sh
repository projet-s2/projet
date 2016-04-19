echo -ne '|>                   |(0%)\r'
javac -d bin/tournoi src/tournoi/Joueur.java -cp bin/paquetage
echo -ne '|---->               |(20%)\r'
javac -d bin/tournoi src/tournoi/Paire.java -cp bin/paquetage
echo -ne '|-------->           |(40%)\r'
javac -d bin/tournoi src/tournoi/Match.java -cp bin/paquetage
echo -ne '|------------>       |(60%)\r'
javac -d bin/tournoi src/tournoi/Terrain.java -cp bin/paquetage
echo -ne '|---------------->   |(80%)\r'
javac -d bin/tournoi src/tournoi/Tournoi.java -cp bin/paquetage
echo -ne '|--------------------|(100%)\r'
echo -ne '\n'
echo "Compilation terminé."
