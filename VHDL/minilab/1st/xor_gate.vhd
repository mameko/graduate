entity xor_gate is
port(a,b: in bit;
    c: out bit);
end entity;

architecture dataflow of xor_gate is
  begin
    c<= a xor b after 2ns;
end dataflow;
