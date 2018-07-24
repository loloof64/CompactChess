package sh.hell.compactchess;

import sh.hell.compactchess.engine.Engine;
import sh.hell.compactchess.exceptions.ChessException;
import sh.hell.compactchess.game.AlgebraicNotationVariation;
import sh.hell.compactchess.game.Game;
import sh.hell.compactchess.game.GameStatus;
import sh.hell.compactchess.game.Move;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main
{
	public static void main(String[] args) throws IOException, ChessException
	{
		System.out.println();
		main();
	}

	public static void main() throws IOException, ChessException
	{
		System.out.println("# CompactChess");
		System.out.println();
		System.out.println("CompactChess is a library which you can use to do chess-related stuff with.");
		System.out.println("However, this little UI can help as well without any coding required.");
		System.out.println();
		System.out.println("- [C]onvert Notation");
		System.out.println("- [E]ngine Operations");
		System.out.println("- E[x]it");
		System.out.println();
		do
		{
			System.out.print("Your choice: ");
			final char selection = (char) System.in.read();
			while(System.in.available() > 0)
			{
				System.in.read();
			}
			System.out.println();
			switch(selection)
			{
				case 'C':
				case 'c':
					convertNotation();
					return;

				case 'E':
				case 'e':
					engineOperations();
					return;

				case 'X':
				case 'x':
					return;
			}
		}
		while(true);
	}

	public static void convertNotation() throws IOException, ChessException
	{
		System.out.println("# Compact Chess > Convert Notation");
		System.out.println();
		System.out.println("What do you want to convert form?");
		System.out.println();
		System.out.println("- [F]EN");
		System.out.println("- [P]GN");
		System.out.println("- PGN F[i]le");
		System.out.println("- [C]GN File");
		System.out.println("- E[x]it");
		System.out.println();
		do
		{
			System.out.print("Your choice: ");
			final char selection = (char) System.in.read();
			while(System.in.available() > 0)
			{
				System.in.read();
			}
			System.out.println();
			switch(selection)
			{
				case 'F':
				case 'f':
					convertNotationFromFEN();
					return;

				case 'P':
				case 'p':
					convertNotationFromPGN();
					return;

				case 'i':
				case 'I':
					convertNotationFromPGNFile();
					return;

				case 'c':
				case 'C':
					convertNotationFromCGNFile();
					return;

				case 'X':
				case 'x':
					main();
					return;
			}
		}
		while(true);
	}

	public static void convertNotationFromFEN() throws ChessException, IOException
	{
		System.out.println("# Compact Chess > Convert Notation > From FEN");
		System.out.println();
		System.out.print("FEN: ");
		final String fen = new Scanner(System.in).useDelimiter("\\n").next();
		System.out.println();
		convertNotationTo(new Game().loadFEN(fen).start());
	}

	public static void convertNotationFromPGN() throws ChessException, IOException
	{
		System.out.println("# Compact Chess > Convert Notation > From PGN");
		System.out.println();
		System.out.println("Paste your PGN and terminate it with a line containing a minus (-):");
		final StringBuilder pgn = new StringBuilder();
		final Scanner scanner = new Scanner(System.in).useDelimiter("\\n");
		do
		{
			String line = scanner.next();
			if(line.equals("-"))
			{
				break;
			}
			else
			{
				pgn.append(line).append("\n");
			}
		}
		while(true);
		System.out.println();
		convertNotationTo(Game.fromPGN(pgn.toString()).get(0));
	}

	public static void convertNotationFromPGNFile() throws ChessException, IOException
	{
		System.out.println("# Compact Chess > Convert Notation > From PGN File");
		System.out.println();
		System.out.print("File: ");
		final String file = new Scanner(System.in).useDelimiter("\\n").next();
		System.out.println();
		final Scanner scanner = new Scanner(file);
		scanner.useDelimiter("\\A");
		String content = scanner.next();
		scanner.close();
		convertNotationTo(Game.fromPGN(content).get(0));
	}

	public static void convertNotationFromCGNFile() throws ChessException, IOException
	{
		System.out.println("# Compact Chess > Convert Notation > From CGN File");
		System.out.println();
		System.out.print("File: ");
		final String file = new Scanner(System.in).useDelimiter("\\n").next();
		System.out.println();
		convertNotationTo(Game.fromCGN(new FileInputStream(file)).get(0));
	}

	public static void convertNotationTo(final Game game) throws ChessException, IOException
	{
		System.out.println("# Compact Chess > Convert Notation > Target");
		System.out.println();
		System.out.println("What do you want to convert to?");
		System.out.println();
		System.out.println("- [F]EN");
		System.out.println("- [P]GN");
		System.out.println("- FA[N]");
		System.out.println("- [C]GN File");
		System.out.println("- [S]VG");
		System.out.println("- E[x]it");
		System.out.println();
		do
		{
			System.out.print("Your choice: ");
			final char selection = (char) System.in.read();
			while(System.in.available() > 0)
			{
				System.in.read();
			}
			System.out.println();
			switch(selection)
			{
				case 'F':
				case 'f':
					System.out.println(game.getFEN());
					System.out.println();
					convertNotationTo(game);
					return;

				case 'P':
				case 'p':
					System.out.println(game.toPGN());
					System.out.println();

					convertNotationTo(game);
					return;

				case 'N':
				case 'n':
					System.out.println(game.toPGN(true, AlgebraicNotationVariation.FAN));
					System.out.println();
					convertNotationTo(game);
					return;

				case 'C':
				case 'c':
					convertNotationToCGNFile(game);
					return;

				case 'S':
				case 's':
					System.out.println(game.toSVG());
					System.out.println();
					convertNotationTo(game);
					return;

				case 'X':
				case 'x':
					convertNotation();
					return;
			}
		}
		while(true);
	}

	public static void convertNotationToCGNFile(final Game game) throws ChessException, IOException
	{
		System.out.println("# Compact Chess > Convert Notation > To CGN File");
		System.out.println();
		System.out.print("File: ");
		final String file = new Scanner(System.in).useDelimiter("\\n").next();
		System.out.println();
		FileOutputStream os = new FileOutputStream(file);
		game.toCGN(os);
		os.close();
		convertNotationTo(game);
	}

	public static void engineOperations() throws IOException, ChessException
	{
		System.out.println("# CompactChess > Engine Operations > Configure Engine");
		System.out.println();
		int threads = Runtime.getRuntime().availableProcessors() - 1;
		if(threads < 1)
		{
			threads = 1;
		}
		System.out.println("You have " + Runtime.getRuntime().availableProcessors() + " logical processor(s), so I'll give the engine " + threads + " thread(s).");
		System.out.println();
		System.out.print("Engine Binary: ");
		final String binary = new Scanner(System.in).useDelimiter("\\n").next();
		System.out.println();
		final Engine engine = new Engine(binary, threads);
		final Game game = new Game().loadFEN("4k3/8/R3K3/8/8/8/8/8 w - -").start();
		final long timeStart = System.currentTimeMillis();
		final Move move = engine.evaluate(game, 3000).awaitConclusion().getBestMove();
		if(move == null)
		{
			System.out.println("Aborting: Engine failed to solve a mate in 1 in less than 3 seconds.");
			return;
		}
		else if(move.isCheckmate())
		{
			System.out.println("Engine solved a mate in 1 in about " + (System.currentTimeMillis() - timeStart) + " ms.");
		}
		else
		{
			System.out.println("Aborting: Engine didn't correctly solve a mate in 1 in less than 3 seconds.");
			System.out.println("If this is your custom engine, you might want to fix that:");
			System.out.println();
			System.out.println(move.commit().toPGN());
			return;
		}
		System.out.println();
		engineOperations(engine);
	}

	public static void engineOperations(final Engine engine) throws IOException, ChessException
	{
		System.out.println("# CompactChess > Engine Operations");
		System.out.println();
		System.out.println("What do you want to do with this engine?");
		System.out.println();
		System.out.println("- [F]inish Position");
		System.out.println("- [D]ispose of Engine");
		System.out.println();
		do
		{
			System.out.print("Your choice: ");
			final char selection = (char) System.in.read();
			while(System.in.available() > 0)
			{
				System.in.read();
			}
			System.out.println();
			switch(selection)
			{
				case 'F':
				case 'f':
					engineOperationsFinishPosition(engine);
					return;

				case 'D':
				case 'd':
					engine.dispose();
					main();
					return;
			}
		}
		while(true);
	}

	public static void engineOperationsFinishPosition(final Engine engine) throws ChessException, IOException
	{
		System.out.println("# CompactChess > Engine Operations > Finish Position");
		System.out.println();
		System.out.print("FEN: ");
		final String fen = new Scanner(System.in).useDelimiter("\\n").next();
		System.out.println();
		System.out.print("Depth: ");
		final int depth = Integer.valueOf(new Scanner(System.in).useDelimiter("\\n").next());
		System.out.println();
		final Game game = new Game().loadFEN(fen).start();
		engine.debug(true);
		do
		{
			final Move move = engine.evaluateDepth(game, depth).awaitConclusion().getBestMove();
			if(move == null)
			{
				System.out.println("No further move suggested.");
				break;
			}
			System.out.println(game.toMove.name() + " played " + move.toUCI() + "\n");
			move.commit();
			System.out.println(game.toString(true));
			System.out.println(game.getFEN() + "\n");
			System.out.println(game.toPGN() + "\n");
			System.out.println(game.toPGN(AlgebraicNotationVariation.FAN) + "\n");
			if(game.status != GameStatus.ONGOING)
			{
				System.out.println("Game over: " + game.status.name() + " by " + game.endReason.name());
				break;
			}
		}
		while(true);
		System.out.println();
		engineOperations(engine);
	}
}
