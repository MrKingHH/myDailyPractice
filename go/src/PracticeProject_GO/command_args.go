package main

import (
	"fmt"
	"errors"
	"io"
	"os"
	"strings"
)

var (
	ErrUsage = errors.New("usgae")
	ErrUnknownCommand = errors.New("unknown command")
)

type Main struct{
	Stdin  io.Reader
	Stdout io.Writer
	Stderr io.Writer
}

func NewMain() *Main{
	return &Main{
		Stdin: os.Stdin,
		Stdout: os.Stdout,
		Stderr: os.Stderr,
	}
}

func (m *Main) Usgae() string{
	return strings.TrimLeft(`
This is a command test program
Usage:
    jyx command [arguments]
The commands are:
    wjez        print high school
    aufe        print the first University
    hfut        print the second University
    help        print this screen
`,"")
}

func (m *Main) Run(args ...string) error{
     if len(args)==0 || strings.HasPrefix(args[0], "-"){
		fmt.Fprintf(m.Stdout, m.Usgae())
		return ErrUsage
	 }
	//Execute command
	switch(args[0]){
	case "help" :
		fmt.Fprintf(m.Stdout, m.Usgae())
		return ErrUsage
	case "wjez":
        return newWjezCommand(m).Run()
	case "aufe":
		return newAufeCommand(m).Run()
	case "hfut":
		return newHfutCommand(m).Run()
	default:
		return ErrUnknownCommand
	}
}

//得到命令行参数 传递给Run方法
func main(){
	m := NewMain()
	//args[0]是命令的路径，命令本身的名字
	if err := m.Run(os.Args[1:]...); err == ErrUsage{
		os.Exit(2)
	}else if err != nil{
		fmt.Println(err.Error())
		os.Exit(1)
	}
}

type WjezCommand struct {
	Stdin io.Reader
	Stdout io.Writer
	Stderr io.Writer
}

func newWjezCommand(m *Main) *WjezCommand{
	return &WjezCommand{
		Stdin: m.Stdin,
		Stdout: m.Stdout,
		Stderr: m.Stderr,
	}
}

func (cmd *WjezCommand) Run(args ...string) error{

	_ , err :=fmt.Fprintf(cmd.Stdout, "望江县第二中学");
	if err != nil{
		return err
	}
	return nil
}

type AufeCommand struct {
	Stdin io.Reader
	Stdout io.Writer
	Stderr io.Writer
}

func newAufeCommand(m *Main) *AufeCommand{
	return &AufeCommand{
		Stdin: m.Stdin,
		Stdout: m.Stdout,
		Stderr: m.Stderr,
	}
}

func (cmd *AufeCommand) Run() error{
	if _, err := fmt.Fprintf(cmd.Stdout, "安徽财经大学"); err != nil{
		return err
	}else{
		return nil
	}
}

type HfutCommand struct {
	Stdin io.Reader
	Stdout io.Writer
	Stderr io.Writer
}

func newHfutCommand(m *Main) *HfutCommand{
	return &HfutCommand{
		Stdin: m.Stdin,
		Stdout: m.Stdout,
		Stderr: m.Stderr,
	}
}

func (cmd *HfutCommand) Run() error{
	if _, err := fmt.Fprintf(cmd.Stdout, "合肥工业大学"); err != nil{
		return err
	}else{
		return nil
	}
}
