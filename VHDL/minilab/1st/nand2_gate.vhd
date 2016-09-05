entity nand2_gate is
port(a,b: in bit;
     c: out bit);
end nand2_gate;

architecture dataflow of nand2_gate is
  
  begin
    c <= a nand b;
end dataflow;
