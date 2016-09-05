entity decoder_2to4_tb is
end entity;

architecture io of decoder_2to4_tb is
  
  component decoder_2to4 is
    port(a, b : in bit;
     out0, out1, out2, out3 : out bit);
  end component;
  
  for all: decoder_2to4 use entity work.decoder_2to4(dataflow);
  
  signal a,b,o0,o1,o2,o3: bit;
  
  begin
    c1: decoder_2to4 port map(a,b,o0,o1,o2,o3);
      
    a <= '0', '0' after 10ns, '1' after 20ns, '1' after 30ns;
    b <= '0', '1' after 10ns, '0' after 20ns, '1' after 30ns;
end io;