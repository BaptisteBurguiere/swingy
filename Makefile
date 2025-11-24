NAME = swingy
VERSION = 1.0-SNAPSHOT
EXEC = target/$(NAME)-$(VERSION).jar

$(EXEC):
	mvn package

$(NAME): $(EXEC)

all: $(NAME)

clean:
	mvn clean

re: clean all

run:
	java -Djava.awt.headless=false -jar $(EXEC)

.PHONY: $(NAME) all clean re run
