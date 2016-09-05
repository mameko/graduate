entity mux_4to1 is
  port(a,b,c,d : in bit;
       s : in bit_vector(1 downto 0);
       f : out bit);
end mux_4to1;
  
architecture dataflow of mux_4to1 is 
  component and3_gate is
    port(a,b,c: in bit;
       d: out bit);
  end component;
  
  component or2 is
    port(a,b: in bit;
         c: out bit);
  end component;
  
  component not_gate is
    port(a: in bit;
       c: out bit);
  end component;
  
  for all: and3_gate use entity work.and3_gate(dataflow);
  for all: or2 use entity work.or2(dataflow);
  for all: not_gate use entity work.not_gate(dataflow);
   
  signal im_not_s0, im_not_s1, im_anots1nots0, im_bnots1s0, im_cs1nots0, im_ds1s0, im_1or2, im_3or4 : bit;
  
  begin 
    c1: not_gate port map(s(0),im_not_s0);
    c2: not_gate port map(s(1),im_not_s1);
      
    c3: and3_gate port map(a,im_not_s0,im_not_s1,im_anots1nots0);
    c4: and3_gate port map(b,s(0),im_not_s1,im_bnots1s0);  
    c5: and3_gate port map(c,s(1),im_not_s0,im_cs1nots0); 
    c6: and3_gate port map(d,s(0),s(1),im_ds1s0);
    
    c7: or2 port map(im_anots1nots0, im_bnots1s0, im_1or2);
    c8: or2 port map(im_cs1nots0, im_ds1s0, im_3or4);
    
    c9: or2 port map(im_1or2, im_3or4, f);
      
end dataflow;
