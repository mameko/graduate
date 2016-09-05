entity and2 is
  port(a, b : in bit;
      c : out bit);
end and2;
    
architecture dataflow of and2 is
   begin
     c <= a and b after 2ns;
end dataflow;
