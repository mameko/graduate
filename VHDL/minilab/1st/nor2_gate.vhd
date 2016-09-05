entity nor2_gate is
port(a,b: in bit;
     c: out bit);
end nor2_gate;

architecture dataflow of nor2_gate is
  
  begin
    c <= a nor b;
end dataflow;
  
   
