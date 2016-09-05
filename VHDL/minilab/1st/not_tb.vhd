entity not_tb is
end entity;

architecture io of not_tb is
  component not_gate is
    port(a: in bit;
       c: out bit);
  end component;
  
  component and2 is
    port(a,b: in bit;
       c: out bit);
  end component;
  
  for all: not_gate use entity work.not_gate(dataflow);
  for all: and2 use entity work.and2(dataflow);
  
  signal a: bit;
  signal b: bit;
  signal c: bit;
  signal d: bit;
  
  begin
    c1: not_gate port map(a,c);
    c2: and2 port map(a,b,d);
    
    a <= '0','1' after 10ns,'0' after 20ns, '1' after 30ns;
    b <= '0','1' after 10ns,'1' after 20ns, '0' after 30ns;
end io;
