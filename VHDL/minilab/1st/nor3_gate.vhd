entity nor3_gate is
  port(a,b,c: in bit;
       d: out bit);
end entity;

architecture dataflow of nor3_gate is
signal im0 : bit;
begin
  im0 <= a or b or c; 
  d <= not im0 after 3ns;
end dataflow;
