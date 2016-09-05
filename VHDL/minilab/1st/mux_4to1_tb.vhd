entity mux_4to1_tb is
end entity;

architecture io of mux_4to1_tb is
  component mux_4to1 is
    port(a,b,c,d : in bit;
       s : in bit_vector(1 downto 0);
       f : out bit);
  end component;
  
  for all: mux_4to1 use entity work.mux_4to1(dataflow);
  
  signal a,b,c,d,f : bit;
  signal s : bit_vector(1 downto 0);
  
  begin
    c1: mux_4to1 port map(a,b,c,d,s,f);
    
    a <= '1','0' after 10ns, '1' after 20ns, '0' after 30ns,'1' after 40ns, '0' after 50ns;
    b <= '1','0' after 10ns, '1' after 20ns, '0' after 30ns,'1' after 40ns, '0' after 50ns;
    c <= '1','0' after 10ns, '1' after 20ns, '0' after 30ns,'1' after 40ns, '0' after 50ns;
    d <= '1','0' after 10ns, '1' after 20ns, '0' after 30ns,'1' after 40ns, '0' after 50ns;
    s <= "00","01" after 50ns,"10" after 100ns, "11" after 150ns;
    
  end io;
