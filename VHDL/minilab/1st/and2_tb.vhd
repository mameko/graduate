entity and2_tb is
end entity;

architecture io of and2_tb is
  component and2
     port(a, b : in bit;
      c : out bit);
  end component;
  
  for all : and2 use entity work.and2(dataflow); 
  signal a: bit;
    signal b: bit;
    signal c: bit; 
    
  begin
    c1: and2 port map(a ,b, c);
    
    
    
    a <= '0','1' after 10ns,'0' after 20ns, '1' after 30ns;
    b <= '0','1' after 10ns,'1' after 20ns, '0' after 30ns;
end io;  
