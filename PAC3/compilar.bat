@ echo on

javac edu\uoc\tdp\pac3\PeticionsException.java
javac edu\uoc\tdp\pac3\PeticionsInterface.java
javac edu\uoc\tdp\pac3\TDSLanguageUtils.java
javac edu\uoc\tdp\pac3\client\Client.java
javac edu\uoc\tdp\pac3\client\PantallaClient.java
javac edu\uoc\tdp\pac3\server\GestorBDD.java
javac edu\uoc\tdp\pac3\server\PantallaServidor.java
javac edu\uoc\tdp\pac3\server\PeticionsImpl.java
javac edu\uoc\tdp\pac3\server\Servidor.java
javac edu\uoc\tdp\pac3\server\entitats\Campanya.java
javac edu\uoc\tdp\pac3\server\entitats\Empleat.java
javac edu\uoc\tdp\pac3\server\entitats\Peticio.java
javac edu\uoc\tdp\pac3\server\entitats\Taller.java

rmic edu.uoc.tdp.pac3.server.PeticionsImpl
