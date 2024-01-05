![](https://github.com/vlmaier/wk/actions/workflows/build.yml/badge.svg)

# wk - the wc clone

Coding Challenge #1 - Build your own wc!

The functional requirements for `wc` are concisely described by it’s man page - give it a go in your local terminal now:

```shell
man wc
```

The TL/DR version is: wc – word, line, character, and byte count.

## Usage

### Clone

```shell
git clone git@github.com:vlmaier/wk.git
```

### Install

```shell
./install
```

### Run

```shell
wk -h
```

```console
Usage: wk [<options>] [<filename>]

Options:
  -c          The number of bytes in the input file or stdin is written to the stdout.
  -l          The number of lines in the input file or stdin is written to the stdout.
  -m          The number of characters in the input file or stdin is written to the stdout. If the current locale does not
              support multibyte characters, this is equivalent to the -c option. This will cancel out any prior usage of
              the -c option.
  -w          The number of words in the input file or stdin is written to the stdout.
  -h, --help  Show this message and exit
```