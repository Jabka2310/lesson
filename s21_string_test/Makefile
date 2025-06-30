# Compiler and base flags
CC = gcc
CFLAGS = -Werror -Wall -Wextra -std=c11


# OS-specific flags for tests
UNAME_S := $(shell uname -s)
ifeq ($(UNAME_S),Darwin)
	# macOS with Homebrew
	OPEN_CMD = open
	BREW_PREFIX := $(shell brew --prefix)
	TEST_FLAGS = -I$(BREW_PREFIX)/include -L$(BREW_PREFIX)/lib -lcheck -lm
else
	# Linux with pkg-config
	OPEN_CMD = xdg-open
	TEST_FLAGS = $(shell pkg-config --cflags --libs check) -lm
endif

# Source files for the library
SRC_FILES = $(wildcard 15_functions/*.c)
OBJ_FILES = $(patsubst %.c,%.o,$(SRC_FILES))
TEST_FILES = $(wildcard tests/*.c)

# Default target
.PHONY: all
all: clean s21_string.a

# Rule to build the static library
s21_string.a: $(OBJ_FILES)
	ar rcs $@ $^
	ranlib $@

# Rule to compile object files
15_functions/%.o: 15_functions/%.c
	$(CC) $(CFLAGS) -c $< -o $@

# Rule to build and run tests
.PHONY: test
test: s21_string.a
	$(CC) $(CFLAGS) -o tests/test $(TEST_FILES) s21_string.a $(TEST_FLAGS)
	./tests/test

# Rule to generate and view coverage report
.PHONY: gcov_report
gcov_report: CFLAGS += --coverage
gcov_report: clean test
	@echo "Generating coverage report..."
	lcov --capture --directory ./15_functions --output-file coverage.info
	lcov --remove coverage.info '*/tests/*' --output-file coverage.info  --ignore-errors unused
	genhtml coverage.info --output-directory ./gcov_report >/dev/null
	@echo "HTML report generated in ./gcov_report/index.html"
	@echo "Run 'make open_report' to view it."

# Rule to open the coverage report
.PHONY: open_report
open_report:
	@$(OPEN_CMD) ./gcov_report/index.html

# Rule to clean up build artifacts
.PHONY: clean
clean:
	rm -rf *.o *.a *.gcda *.gcno s21_string.a tests/test tests/*.o tests/*.a tests/*.dSYM tests/*.gcda tests/*.gcno 15_functions/*.o 15_functions/*.gcda 15_functions/*.gcno gcov_report coverage.info

# Rule to rebuild the project
.PHONY: rebuild
rebuild: clean all