entity shift_register_32 is
	port (l : in bit_vector (31 downto 0);
		  sri, clk, rstb : in bit;
		  s : in bit_vector (1 downto 0);
		  q : out bit_vector (31 downto 0));
end entity;

architecture struct of shift_register_32 is
	component mux_4to1 is
		port(a,b,c,d : in bit;
		     s : in bit_vector(1 downto 0);
		     f : out bit);
	end component;
	
	component dff is
	  port( d,clk,rstb : in bit;
			q,not_q : out bit);
	end component;	
	
	for all :  mux_4to1 use entity work.mux_4to1(dataflow);
	for all :  dff use entity work.dff(dataflow);
	
	signal im_din, im_qs : bit_vector (31 downto 0);	
	
	begin
		shift_register : for i in 0 to 31 generate
		
			ls_bit : if i = 0 generate
			ls_cell :mux_4to1 port map (l(0),im_qs(1),sri,im_qs(0),s, im_din(0));
			ls_cell2 : dff port map (im_din(0),clk,rstb,im_qs(0));
			q(0) <= im_qs(0);
			end generate ls_bit;
	
			middle_bit : if i > 0 and i < 31 generate
			middle_cell : mux_4to1 port map (l(i),im_qs(i+1),im_qs(i-1),im_qs(i),s, im_din(i));
			middle_cell2 : dff port map (im_din(i),clk,rstb,im_qs(i));
			q(i) <= im_qs(i);
			end generate middle_bit;
		
			ms_bit : if i = 31 generate
			ms_cell : mux_4to1 port map (l(i),im_qs(i),im_qs(i-1),im_qs(i),s, im_din(i));
			ms_cell2 : dff port map (im_din(i),clk,rstb,im_qs(i));
			q(i) <= im_qs(i);
			end generate ms_bit;

		end generate shift_register;
	
end struct;