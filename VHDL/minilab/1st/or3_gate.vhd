entity or3_gate is
  port(a,b,c: in bit;
       d: out bit);
end entity;

architecture dataflow of or3_gate is
begin
  d <= ((a or b) or c )after 3ns;
end dataflow;
