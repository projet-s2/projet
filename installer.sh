echo -ne '|>                   |(0%)\r'
javac -d bin/paquetage src/Joueur.java -cp bin/paquetage
echo -ne '|---->               |(20%)\r'
javac -d bin/paquetage src/Paire.java -cp bin/paquetage
echo -ne '|-------->           |(40%)\r'
javac -d bin/paquetage src/Match.java -cp bin/paquetage
echo -ne '|------------>       |(60%)\r'
javac -d bin/paquetage src/Terrain.java -cp bin/paquetage
echo -ne '|---------------->   |(80%)\r'
javac -d bin/paquetage src/Tournoi.java -cp bin/paquetage
echo -ne '|--------------------|(100%)\r'
echo -ne '\n'
echo "Compilation terminé."
