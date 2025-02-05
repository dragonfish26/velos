# Variables
JAVAC = javac
JAVA = java
JAVADOC = javadoc
SOURCE_DIR = src
TEST_DIR = tests
DOC_DIR = docs
CLASSES_DIR = classes
JUNIT_JAR = junit-console.jar
JAR_DIR = jar
JAR_FILE = $(JAR_DIR)/velos.jar

rwildcard = $(wildcard $1) $(foreach d,$(wildcard $1/*),$(call rwildcard,$d))

JAVA_FILES := $(wildcard $(SOURCE_DIR)/**/*.java)
TEST_FILES := $(filter %.java,$(call rwildcard,$(TEST_DIR)))
TEST_RUN := $(wildcard *.java)

# Targets
all: cls doc test

cls: $(JAVA_FILES)
	$(JAVAC) -sourcepath $(SOURCE_DIR) -d $(CLASSES_DIR) $^

doc: $(JAVA_FILES)
	$(JAVADOC) -sourcepath $(SOURCE_DIR) -d $(DOC_DIR) $^

test: cls $(TEST_FILES)
	$(JAVAC) -classpath $(JUNIT_JAR):$(CLASSES_DIR) -d $(CLASSES_DIR) $(TEST_FILES)

test-run:
	$(JAVA) -jar $(JUNIT_JAR) \
		--class-path $(CLASSES_DIR) \
		--scan-classpath

run: cls
	$(JAVA) -classpath $(CLASSES_DIR) simVlille.Main

$(JAR_FILE): cls
	jar cvfe $(JAR_FILE) simVlille.Main -C $(CLASSES_DIR) .

velos.jar: $(JAR_FILE)
	java -jar $(JAR_FILE)

clean:
	rm -rf $(CLASSES_DIR)
	rm -rf $(DOC_DIR)

.PHONY: all cls doc test clean main
