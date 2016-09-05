entity full_adder is
  port(a,b,cin : in bit;
       cout,sum: out bit);
end entity;

architecture dataflow of full_adder is
  component and2 is
    port(a, b : in bit;
      c : out bit);
  end component;
  
  component xor_gate is
    port(a, b : in bit;
      c : out bit);
  end component;
  
  component or3_gate is
    port(a, b, c: in bit;
      d : out bit);
  end component;

--signal declaration  
   signal im_a_and_b : bit;
   signal im_a_and_cin : bit;
   signal im_b_and_cin : bit;
   signal im_a_xor_b : bit;

--binding   
   for all: xor_gate use entity work.xor_gate(dataflow);
   for all: and2 use entity work.and2(dataflow);   
   for all: or3_gate use entity work.or3_gate(dataflow);   
   
   begin
      c1: and2 port map(a,b,im_a_and_b);
      c2: and2 port map(a,cin,im_a_and_cin); 
      c3: and2 port map(b,cin,im_b_and_cin);  
      
      c: or3_gate port map(im_a_and_b, im_a_and_cin, im_b_and_cin, cout);
     
      c4: xor_gate port map(a,b,im_a_xor_b);
      
      s: xor_gate port map(im_a_xor_b, cin, sum);
        
end dataflow;
        
        
   