mock: ;drakov -f docs.md --autoOptions --watch

docs: ;aglio --no-theme-condense --theme-variables slate  -i docs.md -o src/main/resources/docs.html