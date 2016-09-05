entity nand2_gate_tb is
end entity;

architecture io of nand2_gate_tb is
  component nand2_gate is
    port(a,b: in bit;
     c: out bit);
  end component;
 
  for all: nand2_gate use entity work.nand2_gate(dataflow);
  
  signal a : bit;
  signal b : bit;
  signal c : bit;
  
  begin 
    c1: nand2_gate port map(a,b,c);
      
    a <= '0','1' after 10ns,'0' after 20ns, '1' after 30ns;
    b <= '0','1' after 10ns,'1' after 20ns, '0' after 30ns;
end io;
