entity full_adder_tb is
end entity;

architecture io of full_adder_tb is
  
  component full_adder is
    port(a,b,cin : in bit;
       cout,sum: out bit);
  end component;

--signal declaration  
  signal a : bit;
  signal b : bit;
  signal cin : bit;
  signal cout : bit;
  signal sum : bit;
 
--binding 
  for all: full_adder use entity work.full_adder(dataflow);
  
  begin
    c1: full_adder port map(a,b,cin,cout,sum);
  
    a <= '0','0' after 10ns, '0' after 20ns, '0' after 30ns, '1' after 40ns, '1' after 50ns, '1' after 60ns, '1' after 70ns;
    b <= '0','0' after 10ns, '1' after 20ns, '1' after 30ns, '0' after 40ns, '0' after 50ns, '1' after 60ns, '1' after 70ns;
    cin <= '0','1' after 10ns, '0' after 20ns, '1' after 30ns, '0' after 40ns, '1' after 50ns, '0' after 60ns, '1' after 70ns;
      
end io;
  
  