entity or2_tb is
end entity;

architecture io of or2_tb is
  component or2
    port(a,b : in bit;
      c : out bit);
  end component;
  
  for all: or2 use entity work.or2(dataflow);
  
  signal a : bit;
  signal b : bit;
  signal c : bit;
  
  begin 
    c1: or2 port map(a,b,c);
      
    a <= '0','1' after 10ns,'0' after 20ns, '1' after 30ns;
    b <= '0','1' after 10ns,'1' after 20ns, '0' after 30ns;
end io;