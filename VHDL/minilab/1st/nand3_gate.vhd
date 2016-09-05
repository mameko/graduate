entity nand3_gate is
  port(a,b,c: in bit;
       d: out bit);
end entity;

architecture dataflow of nand3_gate is
signal im0 : bit;
begin
  im0 <= a and b and c; 
  d <=not im0 after 3ns;
end dataflow;
