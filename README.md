# File Integrity Checker for JAVA

Extendable file integrity checker for JAVA.

Example use:

	// example logger for various events
	final IntegrityLogger integrityLogger = new IntegrityLogger() {
		@Override
		public void logNewFile(File file, String hash) {
			System.out.println("New file: " + file + " " + hash);
		}

		public void logHashChanged(File file, String oldHash, String newHash) {
			System.out.println("Hash Changed: " + file.getAbsolutePath() + " from: " + oldHash + " to: " + newHash);
		}

		public void logHashUnchanged(File file) {
			System.out.println("Hash OK: " + file.getAbsolutePath());
		}
	};

	// digest strategy - use the SaltedSha256DigestStrategy or implement your own
	final DigestStrategy digest = new SaltedSha256DigestStrategy("THIS_IS_A_SALT_FOR_THE_HASH");
	
	// integrity database interfae - use the PropertiesIntegrityDatabase or implement your own
	final IntegrityDatabase integrityDB = new PropertiesIntegrityDatabase(new File("check.properties"));
	
	// init the file integrity check
	final FileIntegrityCheck fileIntegrityCheck = new FileIntegrityCheck(integrityLogger, digest, integrityDB);

	// check the file, as you want
	fileIntegrityCheck.check(new File("pom.xml"));