entity adder_32 is
	port(a,b : in bit_vector (31 downto 0);
		   sum : out bit_vector (31 downto 0));
end entity;

architecture struct of adder_32 is
	component full_adder is
		 port(a,b,cin : in bit;
			  cout,sum: out bit);
	end component;
	
	for all : full_adder use entity work.full_adder(dataflow);
	
	signal c : bit_vector (32 downto 1);
	signal carry : bit;
	
	begin
		adder : for i in 0 to 31 generate
		
			ls_bit : if i = 0 generate
			ls_cell :full_adder port map (a(0), b(0), '0' , c(1),sum(0));
			end generate ls_bit;

			middle_bit : if i > 0 and i < 31 generate
			middle_cell : full_adder port map (a(i), b(i), c(i), c(i+1),sum(i));
			end generate middle_bit;
		
			ms_bit : if i = 31 generate
			ms_cell : full_adder port map (a(i), b(i), c(i), carry,sum(i));
			end generate ms_bit;
		end generate adder;
end struct;