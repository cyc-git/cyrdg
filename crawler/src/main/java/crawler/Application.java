package crawler;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutionException;

import crawler.impl.AlexaCrawlerImpl;

public class Application {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		new SimpleCommandLineExecutor(args).execute();
	}

	static class SimpleCommandLineExecutor {
		private static final String TOP = "TOP";
		private static final String COUNTRY = "COUNTRY";

		private String action;
		private String country;
		private int number;

		private SimpleCommandLineExecutor(String[] args) {
			parseArgs(args);
		}

		private void execute() throws InterruptedException, ExecutionException {
			AlexaCrawler crawler = new AlexaCrawlerImpl();
			switch (this.action.toUpperCase()) {
			case TOP:
				crawler.top(number).toCompletableFuture().get().forEach(System.out::println);
				break;
			case COUNTRY:
				crawler.country(country).toCompletableFuture().get().forEach(System.out::println);
				break;
			default:
				// Should never happen.
				break;
			}
		}

		private void parseArgs(String[] args) {
			if (args.length < 2) {
				printHelpAndExit(-1);
			}

			Queue<String> arguments = new LinkedList<>(Arrays.asList(args));

			if (arguments.stream().anyMatch(s -> s.equalsIgnoreCase("--help") || s.equalsIgnoreCase("-h"))) {
				printHelpAndExit(0);
			}

			this.action = arguments.poll();

			switch (this.action.toUpperCase()) {
			case TOP:
				try {
					this.number = Integer.parseInt(arguments.poll());
				} catch (NumberFormatException e) {
					System.out.println("Failed to parse number : " + e.getMessage());
					System.out.println();
					printHelpAndExit(-1);
				}
				break;
			case COUNTRY:
				this.country = arguments.poll();
				break;
			default:
				System.out.println("Unknown argument : " + this.action);
				System.out.println();
				printHelpAndExit(-1);
			}

			if (!arguments.isEmpty()) {
				System.out.println("Remain unknown arguments : " + arguments);
				System.out.println();
				printHelpAndExit(-1);
			}
		}

		private void printHelpAndExit(int exitCode) {
			System.out.println("Usage: java -jar crawler.jar <action> <arg1> [<arg2>...]");
			System.out.println();
			System.out.println("\ttop <number>\tshow top <number> sites URL on https://www.alexa.com/topsites/");
			System.out.println(
					"\tcountry <country>\tshow top 20 sites URL on https://www.alexa.com/topsites/ by country");
			System.exit(exitCode);
		}
	}
}
