entity or2 is
  port(a,b : in bit;
      c : out bit);
end or2;

architecture dataflow of or2 is
  
  begin 
    c <= a or b after 2ns;
end dataflow;
    
