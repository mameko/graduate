entity nor2_gate_tb is
end entity;

architecture io of nor2_gate_tb is
  component nor2_gate is 
    port(a,b: in bit;
     c: out bit);
  end component;
  
  for all: nor2_gate use entity work.nor2_gate(dataflow);
  
  signal a: bit;
  signal b: bit;
  signal c: bit;
  
  begin
    c1: nor2_gate port map(a,b,c);
   
    a <= '0','1' after 10ns,'0' after 20ns, '1' after 30ns;
    b <= '0','1' after 10ns,'1' after 20ns, '0' after 30ns;
end io;


