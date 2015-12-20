# iso-slug

Iso-slug is a url-safe slug that is mostly readable and fully reversible (isomorphic, hence the name).

Some examples:
- `hey` -> `hey`
- `hey_jude` -> `hey_jude`
- `hey jude` -> `hey-20-jude`
- `don't make it bad` -> `don-27-t-20-make-20-it-20-bad`

The spec is pretty simple:
- plain characters are kept as is
  - plain characters are:
    - letters from a to z
    - letters from A to Z
    - digits
    - The characters `.` and `_`
- a dash `-` is converted to `--`
- all other characters are converted to their hex representation in UTF-8, delimited by a dash `-`, e.g:
  - space ` ` becomes `-20-`
  - ç becomes `-c3a7-`
  - 友 becomes `-e58f8b-`

Issues for the spec or implementations are welcome.

Licensed under the unlicense.
