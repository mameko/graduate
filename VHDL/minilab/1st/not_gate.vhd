entity not_gate is
  port(a: in bit;
       c: out bit);
end not_gate;

architecture dataflow of not_gate is
  begin
    c <= not a;
end dataflow;
