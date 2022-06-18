package com.a3s;

import java.io.PrintWriter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.a3s.adapter.exception.AdapterException;

public class ExportImportTool {
	private static Log log = LogFactory.getLog(ExportImportTool.class);

	public static void main(String args[]) {

		CommandLine commandLine;
		Options options = new Options();
		try {
			commandLine = prepareCommandLine(args, options);
		} catch (ParseException e) {
			log.error("failed to parse the command line arguments :-", e);
			HelpFormatter helpFormatter = new HelpFormatter();
			helpFormatter.printUsage(new PrintWriter(System.err), 80,
					"ExportImportTool", options);
			return;
		}
		ExportImportToolManager exportImportToolManager = new ExportImportToolManager();
		try {
			exportImportToolManager.processCommandLine(commandLine, options);
		} catch (AdapterException e) {
			log.error(e);
			throw new RuntimeException(e);
		}
	}

	private static CommandLine prepareCommandLine(String[] args, Options options)
			throws ParseException {
		Option optionImport = new Option(ExportImportToolManager.OPTION_IMPORT,
				false, "Import xls to databse table");
		Option optionExport = new Option(ExportImportToolManager.OPTION_EXPORT,
				false, "Export the database Table to XML");
		Option entity = new Option(ExportImportToolManager.OPTION_ENTITY, true,
				"Entity to be Imported or Exported");
		Option inputFileLocation = new Option(
				ExportImportToolManager.OPTION_INPUT_FILE_LOCATION, true,
				"Input file location of the XLS file");
		Option outputFileLocation = new Option(
				ExportImportToolManager.OPTION_OUTPUT_FILE_LOCATION, true,
				"Output location of the XML file");
		Option databaseType = new Option(ExportImportToolManager.OPTION_DATASOURCE_TYPE, true,"Choose the databasetype MSSQL/MYSQL");
		options.addOption(optionImport);
		options.addOption(optionExport);
		options.addOption(entity);
		options.addOption(inputFileLocation);
		options.addOption(outputFileLocation);
		options.addOption(databaseType);
		CommandLineParser commandLineParser = new PosixParser();
		CommandLine commandLine;
		commandLine = commandLineParser.parse(options, args);
		return commandLine;
	}

}
