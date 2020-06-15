# File Integrity Checker for JAVA

Extendable file integrity checker for JAVA.

## How to install in your project?

Maven repo:

    <repositories>
        <repository>
            <id>karsany-repo</id>
            <name>karsany Repository</name>
            <url>https://karsany.dynv6.net/maven/</url>
        </repository>
    </repositories>

Maven dependency:

        <dependency>
            <groupId>hu.karsany</groupId>
            <artifactId>file-integrity-check</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>

## How to use it?

	// example logger for various events
	final IntegrityCheckListener integrityCheckListener = new IntegrityCheckListener() {
		@Override
		public void newFile(IntegrityCheckedFile integrityCheckedFile) {
			System.out.println("New file: " + integrityCheckedFile.file().getAbsolutePath() + " " + integrityCheckedFile.hash());
		}

		public void hashChanged(IntegrityCheckedFile integrityCheckedFile, String oldHash) {
			System.out.println("Hash Changed: " + integrityCheckedFile.file().getAbsolutePath() + " from: " + oldHash + " to: " + integrityCheckedFile.hash());
		}

		public void hashUnchanged(IntegrityCheckedFile integrityCheckedFile) {
			System.out.println("Hash OK: " + integrityCheckedFile.file().getAbsolutePath());
		}
	};

	// digest strategy - use the SaltedSha256DigestStrategy or implement your own
	final DigestStrategy digest = new SaltedSha256DigestStrategy("THIS_IS_A_SALT_FOR_THE_HASH");
	
	// integrity database interfae - use the PropertiesIntegrityDatabase or implement your own
	final IntegrityDatabase integrityDB = new PropertiesFileIntegrityDatabase(new File("check.properties"));
	
	// init the file integrity check
	final FileIntegrityCheck fileIntegrityCheck = new FileIntegrityCheck(integrityCheckListener, digest, integrityDB);

	// check the file, as you want
	fileIntegrityCheck.check(new File("pom.xml"));
