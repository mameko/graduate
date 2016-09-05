entity and3_gate is
  port(a,b,c: in bit;
       d: out bit);
end entity;

architecture dataflow of and3_gate is
begin
  d <= a and b and c after 3ns;
end dataflow;
     
