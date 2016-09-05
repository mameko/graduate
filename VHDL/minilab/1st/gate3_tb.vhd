entity gate3_tb is
end entity;

architecture io of gate3_tb is
 
  component and3_gate is
    port(a,b,c: in bit;
       d: out bit);
  end component;
  
  component or3_gate is
    port(a,b,c: in bit;
       d: out bit);
  end component;
  
  component nand3_gate is
    port(a,b,c: in bit;
       d: out bit);
  end component;
  
  component nor3_gate is
    port(a,b,c: in bit;
       d: out bit);
  end component;
  
  signal in_1 : bit;
  signal in_2 : bit;
  signal in_3 : bit;
  signal out_and3 : bit;
  signal out_or3 : bit;
  signal out_nand3 : bit;
  signal out_nor3 : bit;
    
--binding  
  for all: and3_gate use entity work.and3_gate(dataflow);
  for all: or3_gate use entity work.or3_gate(dataflow);
  for all: nand3_gate use entity work.nand3_gate(dataflow);
  for all: nor3_gate use entity work.nor3_gate(dataflow);
  
  begin
    c1: and3_gate port map(in_1,in_2,in_3,out_and3);
    c2: or3_gate port map(in_1,in_2,in_3,out_or3);
    c3: nand3_gate port map(in_1,in_2,in_3,out_nand3);
    c4: nor3_gate port map(in_1,in_2,in_3,out_nor3);
      
    in_1 <= '0','0' after 10ns, '0' after 20ns, '0' after 30ns, '1' after 40ns, '1' after 50ns, '1' after 60ns, '1' after 70ns;
    in_2 <= '0','0' after 10ns, '1' after 20ns, '1' after 30ns, '0' after 40ns, '0' after 50ns, '1' after 60ns, '1' after 70ns;
    in_3 <= '0','1' after 10ns, '0' after 20ns, '1' after 30ns, '0' after 40ns, '1' after 50ns, '0' after 60ns, '1' after 70ns;
    
 end io;
      
    