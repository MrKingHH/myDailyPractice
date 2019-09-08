package main

import (
	"fmt"
	"github.com/BurntSushi/toml"
	"time"
)

type Config struct {
	Age        int
	Cats       []string
	Pi         float64
	Perfection []int
	DOB        time.Time // requires `import time`
}

const tomlstr = `
Age = 25
Cats = [ "Cauchy", "Plato" ]
Pi = 3.14
Perfection = [ 6, 28, 496, 8128 ]
DOB = 1987-07-05T05:45:00Z
`

func main() {

	var conf Config
	if _, err := toml.Decode(tomlstr, &conf); err != nil {

	}

	fmt.Print(conf)

}
