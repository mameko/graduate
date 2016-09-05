entity decoder_2to4 is
port(a, b : in bit;
     out0, out1, out2, out3 : out bit);
end entity;

architecture dataflow of decoder_2to4 is
  component and2 is
    port(a, b : in bit;
      c : out bit);
  end component;
  
  component not_gate is  
    port(a: in bit;
       c: out bit);
  end component;
  
  for all: and2 use entity work.and2(dataflow);
  for all: not_gate use entity work.not_gate(dataflow);
  
  signal not_a, not_b : bit;
  
  begin
    c1: not_gate port map(a,not_a);
    c2: not_gate port map(b,not_b);
    
    c3: and2 port map(not_a, not_b, out0);
    c4: and2 port map(not_a, b, out1);
    c5: and2 port map(a, not_b, out2);
    c6: and2 port map(a, b, out3);   
     
end dataflow;