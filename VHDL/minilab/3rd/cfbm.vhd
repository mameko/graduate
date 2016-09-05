library ieee;
use ieee.std_logic_1164.all;

entity cfbm is
	port (start, done, ai, ai_1, clk, rstb : in bit;
		  sp, sa : out bit_vector(1 downto 0);
		  add0, sel : out bit);
end entity;

architecture dataflow of cfbm is
	component and2 is
		port(a, b : in bit;
      c : out bit);
	end component;
	
	component and3_gate is
		port(a, b, c : in bit;
			d : out bit);
	end component;
	
	component or2 is
		port(a,b : in bit;
			c : out bit);
	end component;
	
	component or3_gate is
		port(a,b,c : in bit;
		  d : out bit);
	end component;
	
	component dff is
		port( d,clk,rstb : in bit;
			 q,not_q : out bit);
	end component;
	
	component not_gate is
		port(a: in bit;
		   c: out bit);
	end component;
	
	for all: and2 use entity work.and2(dataflow);
	for all: and3_gate use entity work.and3_gate(dataflow);
	for all: or2 use entity work.or2(dataflow);
	for all: or3_gate use entity work.or3_gate(dataflow);
	
	signal im_qs : bit_vector(6 downto 0) := "0000001";--begin at state s0(load all data); 
	signal im_ds : bit_vector(6 downto 0) := "0000001";
	signal im_not_done, im_not_ai, im_not_ai_1 : bit;
	signal im : bit_vector(20 downto 0);
	
--	signal s0: bit_vector(6 downto 0) := "0000001";
--	signal s1: bit_vector(6 downto 0) := "0000010";
--  signal s2: bit_vector(6 downto 0) := "0000100";
--	signal s3: bit_vector(6 downto 0) := "0001000";
--	signal s4: bit_vector(6 downto 0) := "0010000";
--	signal s5: bit_vector(6 downto 0) := "0100000";
--	signal s6: bit_vector(6 downto 0) := "1000000";

	signal s0,s1,s2,s3,s4,s5,s6,s7,init,notinit,k: bit;
	
	begin
--		p0: process(clk)
--		begin
--		if((not im_qs = "0000001")or(not im_qs = "0000010")or(not im_qs = "0000100")or(not im_qs = "0001000")or(not im_qs = "0010000")or(not im_qs = "0100000")or(not im_qs = "1000000")) then
--	-			im_qs <= "0000001";
--			end if;
--			end process p0;
	--	b0: block(now < = 1ns)
	--	begin
	--	  im_qs <= "0000001" when now <= 1ns;
	--	  end block b0;
	--	b1: block(now > 1ns)  
--		begin
	--	  im_qs <= guarded im_qs;
	--	end block b1;
	------------------------decoding-------------------------------
--	b2: block(s6 = '1' or s5 = '1' or s4 = '1' or s3 = '1' or s2 = '1' or s1 = '1' or s0 = '1' )
--	begin
	s7 <= ((((not im_qs(0)) and (not im_qs(1)))and ((not im_qs(2)) and (not im_qs(3)))) and ((not im_qs(4)) and (not im_qs(5)))) and (not im_qs(6));
	s6 <= ((((not im_qs(0)) and (not im_qs(1)))and ((not im_qs(2)) and (not im_qs(3)))) and ((not im_qs(4)) and (not im_qs(5)))) and im_qs(6);
	s5 <= ((((not im_qs(0)) and (not im_qs(1))) and ((not im_qs(2)) and (not im_qs(3))))and ((not im_qs(4)) and im_qs(5))) and (not im_qs(6));
	s4 <= ((((not im_qs(0)) and (not im_qs(1))) and ((not im_qs(2)) and (not im_qs(3))))and (im_qs(4) and (not im_qs(5)))) and (not im_qs(6));
	s3 <= ((((not im_qs(0)) and (not im_qs(1))) and ((not im_qs(2)) and im_qs(3))) and ((not im_qs(4)) and (not im_qs(5)))) and (not im_qs(6));
	s2 <= ((((not im_qs(0)) and (not im_qs(1))) and (im_qs(2) and (not im_qs(3)))) and ((not im_qs(4)) and (not im_qs(5)))) and (not im_qs(6));
	s1 <= ((((not im_qs(0))and im_qs(1)) and ((not im_qs(2)) and (not im_qs(3)))) and ((not im_qs(4)) and (not im_qs(5)))) and (not im_qs(6));
	s0 <= (((im_qs(0) and (not im_qs(1)))and ((not im_qs(2)) and (not im_qs(3)))) and ((not im_qs(4)) and (not im_qs(5)))) and (not im_qs(6));
--	end block b2;
	
	--d0
		c1: and2 port map(s6,start,im_ds(0));
		c0: and2 port map(s7,start,init);
		c: or2 port map(init,im_ds(0),k);
    dff_1: dff port map(k,clk,rstb,im_qs(0));
	--d1	
		c2: not_gate port map(done, im_not_done);		
		c3: and2 port map(s0,im_not_done,im_ds(1));
		dff_2: dff port map(im_ds(1),clk,rstb,im_qs(1));
	--d2	
		c4: not_gate port map(ai, im_not_ai);
		c5: not_gate port map(ai_1, im_not_ai_1);
		c6: and2 port map(ai,ai_1,im(0));
		c7: and2 port map(im_not_ai,im_not_ai_1,im(1));
		c8: or2 port map(im(0),im(1),im(2));
		c9: and3_gate port map(im(2),s5,im_not_done,im(3));
		c10:and2 port map(s1,im(2),im(4));
		c11:or2 port map(im(3),im(4),im_ds(2));
		dff_3: dff port map(im_ds(2),clk,rstb,im_qs(2));
	--d3	
		c12:and3_gate port map(s1,im_not_ai_1,ai,im(5));
		c13:and2 port map(s5,im_not_done, im(6));
		c14:and2 port map(im_not_ai_1,ai, im(7));
		c15:and2 port map(im(6),im(7),im(8));
		c16:or2 port map(im(8),im(5),im_ds(3));
		dff_4: dff port map(im_ds(3),clk,rstb,im_qs(3));
	--d4
		c17:and3_gate port map(s1,ai_1,im_not_ai,im(9));
		c18:and2 port map(ai_1,im_not_ai, im(10));
		c19:and2 port map(im(6),im(10),im(11));
		c20:or2 port map(im(9),im(11),im_ds(4));
		dff_5: dff port map(im_ds(4),clk,rstb,im_qs(4));
	--d5
		c21:or3_gate port map(s2,s3,s4,im_ds(5));
		dff_6: dff port map(im_ds(5),clk,rstb,im_qs(5));
	--d6
		c22:and2 port map(s5,done,im_ds(6));
		dff_7: dff port map(im_ds(6),clk,rstb,im_qs(6));
		
	-----------------------------------output------------------------------------	
	--s1p
		c23:or2 port map(s2,s3,im(12));
		c24:or2 port map(s4,s6,im(13));
		c25:or2 port map(im(12),im(13),sp(1));		
	--s0p
		c26:or3_gate port map(im(12),im(13),s5,sp(0));		
	--s1a
		c27:or3_gate port map(im(12),im(13),s1,sa(1));
	--s0a 
		c28:or2 port map(s1,s5,im(14));
		c29:or3_gate port map(im(12),im(13),im(14),sa(0));
	--add0
		c30:or2 port map(s3,s4,add0);
	--sel
		sel <= s3;
	
end dataflow;		
		