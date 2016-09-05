entity binary_counter_64_tb is
end entity;

architecture io of binary_counter_64_tb is
  component binary_counter_64 is
    port(clk : in bit;
         o : out bit_vector(5 downto 0));
  end component;
  
  for all:  binary_counter_64 use entity work.binary_counter_64(struct);
  
  signal o : bit_vector(5 downto 0);
  signal clk : bit;
    
  begin
    c1: binary_counter_64 port map(clk,o);
    
    clk <= not clk after 5ns;

end io;
      
    